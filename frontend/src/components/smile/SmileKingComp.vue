<template>
  <div align="center">
    <h2 class="display-2 font-weight-bold mb-3">이달의 웃음왕</h2>
    <v-container>
      <v-row justify="center">
        <v-col cols="12" md="4">
          <v-img width="150px" :src="`${publicPath}images/no${1}.png`"></v-img>
          <v-avatar size="300">
            <v-img :src="`${publicPath}images/${smileKing[0].photo}`"></v-img>
          </v-avatar>
          <v-col>
            {{ smileKing[0].user_id }}
          </v-col>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="4">
          <v-img width="100px" :src="`${publicPath}images/no2.png`"></v-img>
          <v-avatar size="200">
            <v-img :src="`${publicPath}images/${smileKing[1].photo}`"></v-img>
          </v-avatar>
          <v-col>
            {{ smileKing[1].user_id }}
          </v-col>
        </v-col>
        <v-col cols="12" md="4">
          <v-img width="100px" :src="`${publicPath}images/no3.png`"></v-img>
          <v-avatar size="200">
            <v-img :src="`${publicPath}images/${smileKing[2].photo}`"></v-img>
          </v-avatar>
          <v-col>
            {{ smileKing[2].user_id }}
          </v-col>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import http from "@/util/http-common.js";

export default {
  name: "SmileKingComp",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      smileKing: []
    };
  },
  created() {
    http
      .get(`/smile/smileKing`)
      .then(res => {
        this.smileKing = res.data;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>

<style>
</style>