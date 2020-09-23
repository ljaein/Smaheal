<template>
  <div>
    <div>
      <audio ref="outputAudioTag" controls autoplay></audio>
      <button ref="microphonebutton" color="success" @click="recordFromMicrophone">Start</button>
      <button @click="stop">stop</button>
      <button @click="loadTransform">play</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "VoiceComp",
  data() {
    return {
      currentlyRecording: false,
      maxRecordingSeconds: 5 * 60,
      globalAudioBuffer: null,
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

      this.$refs.microphonebutton.addEventListener("click", stopFn);

      mediaRecorder.onstop = async function() {
        console.log("Stopped, state = " + mediaRecorder.state);

        let blob = new Blob(chunks, { type: mediaRecorder.mimeType }); //+'; codecs=opus' });//
        let audioURL = window.URL.createObjectURL(blob);

        let audio = document.createElement("audio");
        audio.src = audioURL;
        document.body.appendChild(audio);
        audio.play();

        let arrayBuffer = await (await fetch(audioURL)).arrayBuffer();

        this.globalAudioBuffer = await new AudioContext().decodeAudioData(
          arrayBuffer
        );

        this.currentlyRecording = false;
      };
    },

    async loadTransform() {
      console.dir(this.globalAudioBuffer);
      //   if (this.currentlyRecording) {
      //     alert(
      //       'You\'re currently recording a clip using your microphone. Please click the red "stop recording" button at the top of the page to finalize the recording, then you can click one of the voice transformers to get your transformed audio file.'
      //     );
      //     return;
      //   }

      let outputAudioBuffer = this.megaphoneTransform(this.globalAudioBuffer);
      //   let outputWavBlob = await audioBufferToWaveBlob(outputAudioBuffer);
      //   let audioUrl = window.URL.createObjectURL(outputAudioBuffer);
      let audioTag = this.$refs.outputAudioTag;
      audioTag.src = outputAudioBuffer;
      audioTag.play();
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
    }
  }
};
</script>

<style>
</style>