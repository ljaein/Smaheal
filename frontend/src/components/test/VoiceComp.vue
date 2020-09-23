<template>
  <div>
    <audio id="audio" autoplay></audio>
    <v-btn v-if="!micOn" @click="start">Start</v-btn>
    <v-btn v-else @click="stop">Stop</v-btn>
  </div>
</template>

<script>
export default {
  name: "VoiceComp",
  data() {
    return {
      micOn: false
    };
  },
  created() {},
  methods: {
    start() {
      navigator.mediaDevices
        .getUserMedia({ audio: true })
        .then(this.handleSuccess);
    },
    handleSuccess(stream) {
      document.getElementById("audio").srcObject = stream;
      this.micOn = true;
    },
    stop() {
      if (this.micOn) {
        if (document.getElementById("audio") != null) {
          const stream = document.getElementById("audio").srcObject;
          const tracks = stream.getTracks();
          tracks.forEach(function(track) {
            track.stop();
          });

          document.getElementById("audio").srcObject = null;
        }
        this.micOn = false;
      }
    },

    // self.AudioContext = (self.AudioContext || self.webkitAudioContext);
    async churchTransform(audioBuffer) {
      let ctx = new OfflineAudioContext(
        audioBuffer.numberOfChannels,
        audioBuffer.length,
        audioBuffer.sampleRate
      );

      // Source
      let source = ctx.createBufferSource();
      source.buffer = audioBuffer;

      // Reverb
      let convolver = ctx.createConvolver();
      convolver.buffer = await ctx.decodeAudioData(
        await (await fetch("/audio/impulse-responses/church.wav")).arrayBuffer()
      );

      // Create graph
      source.connect(convolver);
      convolver.connect(ctx.destination);

      // Render
      source.start();
      let outputAudioBuffer = await ctx.startRendering();
      return outputAudioBuffer;
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