<template>
  <div>
    <div>
      <audio id="audio" ref="outputAudioTag" controls></audio>
      <button ref="microphonebutton" color="success" @click="recordFromMicrophone">Start</button>
      <br />
      <button ref="stop" @click="stop">stop</button>
      <br />
      <button @click="loadTransform">play</button>
      <br />
    </div>
  </div>
</template>

<script>
import bufferToWav from "audiobuffer-to-wav";

export default {
  name: "VoiceComp",
  data() {
    return {
      currentlyRecording: false,
      globalAudioBuffer: null
    };
  },
  created() {},
  methods: {
    async recordFromMicrophone() {
      if (this.currentlyRecording) {
        return;
      }
      this.currentlyRecording = true;

      let chunks = [];
      let stream = await navigator.mediaDevices.getUserMedia({
        audio: true,
        video: false
      });
      let mediaRecorder = new MediaRecorder(stream, { mimeType: "video/webm" });

      mediaRecorder.start();

      mediaRecorder.ondataavailable = function(e) {
        chunks.push(e.data);
      };

      mediaRecorder.onstart = function() {
        console.log("Started, state = ", mediaRecorder.state);
      };

      let stopFn = function() {
        mediaRecorder.stop();
      };

      this.$refs.stop.addEventListener("click", stopFn);

      mediaRecorder.onstop = async function() {
        console.log("Stopped, state = " + mediaRecorder.state);

        let blob = new Blob(chunks, { type: mediaRecorder.mimeType });
        let audioURL = window.URL.createObjectURL(blob);

        let audio = document.getElementById("audio");
        audio.src = audioURL;
      };
    },
    stop() {
      this.currentlyRecording = false;
    },

    async loadTransform() {
      let audio = document.getElementById("audio");
      let arrayBuffer = await (await fetch(audio.src)).arrayBuffer();

      this.globalAudioBuffer = await new AudioContext().decodeAudioData(
        arrayBuffer
      );

      let outputAudioBuffer = this.vibratoTransform(this.globalAudioBuffer);
      outputAudioBuffer.then(function(result) {
        var anchor = document.getElementById("audio");

        var wav = bufferToWav(result);
        var blob = new window.Blob([new DataView(wav)], {
          type: "audio/wav"
        });

        var url = window.URL.createObjectURL(blob);
        anchor.src = url;
        anchor.play();
      });
    },

    async megaphoneTransform(audioBuffer) {
      let ctx = new OfflineAudioContext(
        audioBuffer.numberOfChannels,
        audioBuffer.length,
        audioBuffer.sampleRate
      );
      let source = ctx.createBufferSource();
      source.buffer = audioBuffer;

      // Wave shaper
      let waveShaper = ctx.createWaveShaper();
      waveShaper.curve = makeDistortionCurve(30);
      function makeDistortionCurve(amount) {
        var k = typeof amount === "number" ? amount : 50;
        var n_samples = 44100;
        var curve = new Float32Array(n_samples);
        var deg = Math.PI / 180;
        var x;
        for (let i = 0; i < n_samples; ++i) {
          x = (i * 2) / n_samples - 1;
          curve[i] = ((3 + k) * x * 20 * deg) / (Math.PI + k * Math.abs(x));
        }
        return curve;
      }

      let lpf1 = ctx.createBiquadFilter();
      lpf1.type = "lowpass";
      lpf1.frequency.value = 2000.0;
      let lpf2 = ctx.createBiquadFilter();
      lpf2.type = "lowpass";
      lpf2.frequency.value = 2000.0;
      let hpf1 = ctx.createBiquadFilter();
      hpf1.type = "highpass";
      hpf1.frequency.value = 500.0;
      let hpf2 = ctx.createBiquadFilter();
      hpf2.type = "highpass";
      hpf2.frequency.value = 500.0;
      let compressor = ctx.createDynamicsCompressor();
      lpf1.connect(lpf2);
      lpf2.connect(hpf1);
      hpf1.connect(hpf2);
      hpf2.connect(waveShaper);
      waveShaper.connect(compressor);
      compressor.connect(ctx.destination);

      source.connect(lpf1);

      source.start(0);
      return await ctx.startRendering();
    },

    async vibratoTransform(audioBuffer) {
      let ctx = new OfflineAudioContext(
        audioBuffer.numberOfChannels,
        audioBuffer.length,
        audioBuffer.sampleRate
      );

      // Input
      let inputNode = ctx.createBufferSource();
      inputNode.buffer = audioBuffer;

      // Delay
      let delayNode = ctx.createDelay();
      delayNode.delayTime.value = 0.03;

      let osc = ctx.createOscillator();
      let gain = ctx.createGain();
      let wetGain = ctx.createGain();

      gain.gain.value = 0.002; // depth of change to the delay:

      osc.type = "sine";
      osc.frequency.value = 4.5;

      osc.connect(gain);
      gain.connect(delayNode.delayTime);
      inputNode.connect(delayNode);
      delayNode.connect(wetGain);
      wetGain.connect(ctx.destination);

      osc.start(0);
      inputNode.start(0);
      let outputAudioBuffer = await ctx.startRendering();
      return outputAudioBuffer;
    }
  }
};
</script>

<style>
</style>