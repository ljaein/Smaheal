<template>
  <div align="center">
    <h2 class="font-weight-bold" style=" font-size:3rem;display:inline-flex;vertical-align:middel"><v-icon style="font-size:3rem;color:#356859;" class="mr-2">mdi-party-popper</v-icon>이달의 웃음왕<v-icon style="font-size:3rem;color:#356859" class="ml-2">mdi-party-popper</v-icon></h2>
    <h2 class="font-weight-bold mt-0 mb-5" style=" font-size:1.3rem;">액자와 상장이 배달됩니다. 마이페이지를 확인해보세요!</h2>
    <v-container>
      <v-row justify="center">
        <v-col cols="12" md="4" class="p-0 pt-4 img-div">
          <v-avatar size="300">
            <v-img :src="`${publicPath}images/${smileKing[0].photo}`"></v-img>
          </v-avatar>
          <!-- <v-divider class="m-0 mt-4"></v-divider> -->
          <v-col class="d-flex justify-content-center">
            <img :src="`${publicPath}images/no1.png`" style="width:80px;">
            <span style="font-size:3rem;">{{ smileKing[0].userId }}</span>
          </v-col>
        </v-col>
      <!-- </v-row>
      <v-row justify="center"> -->
        <v-col cols="12" md="4" class="p-0 pt-4 img-div" style="border-left:none;">
          <v-avatar size="300">
            <v-img :src="`${publicPath}images/${smileKing[1].photo}`"></v-img>
          </v-avatar>
          <!-- <v-divider class="m-0 mt-4"></v-divider> -->
          <v-col class="d-flex justify-content-center">
          <img width="80px" :src="`${publicPath}images/no2.png`">
            <span style="font-size:3rem;">{{ smileKing[1].userId }}</span>
          </v-col>
        </v-col>
        <v-col cols="12" md="4" class="p-0 pt-4 img-div" style="border-left:none;">
          <v-avatar size="300">
            <v-img :src="`${publicPath}images/${smileKing[2].photo}`"></v-img>
          </v-avatar>
          <!-- <v-divider class="m-0 mt-4"></v-divider> -->
          <v-col class="d-flex justify-content-center">
          <img width="80px" :src="`${publicPath}images/no3.png`">
            <span style="font-size:3rem;">{{ smileKing[2].userId }}</span>
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
      smileKing: [
        {
          userId: "",
          photo: ""
        },
        {
          userId: "",
          photo: ""
        },
        {
          userId: "",
          photo: ""
        }
      ]
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
.img-div{
   /* border:3px solid lightgray;
   border-top-right-radius:20px;
   border-top-left-radius:20px; */
}
</style>