<template>
  <div align="center">
    <br/>
    <flipbook class="flipbook" :pages="pages" v-slot="flipbook" :zooms="[1]">
      <v-btn @click="flipbook.flipLeft">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      <v-btn @click="flipbook.flipRight">
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </flipbook>
  </div>
</template>

<script>
import flipbook from "flipbook-vue";

export default {
  name: "FlipBookComp",
  components: { flipbook },
  data() {
    return {
      pages: [
        null,
        "images/1600847574137.png",
        "images/1601362225543.png",
        "images/1601366107975.png",
        "images/1600847574137.png",
        "images/1601362225543.png",
        "images/1601366107975.png"
      ]
    };
  }
};
</script>

<style>
.flipbook {
  width: 90vw;
  height: 60vh;
}
</style>