<template>
  <div align="center">
    <br />
    <v-card width="800px">
      <v-img width="100%" :src="`${publicPath}images/award.jpg`"
        ><v-container
          style="width: 100%; height: 100%; top:10%; font-size: 2em; font-family: 궁서; font-weight: bold; position: absolute;"
        >
          <v-row>
            <v-col align="left" style="left:15%">
              제 2020-10호
            </v-col>
          </v-row>
          <br/>
          <v-row>
            <v-col align="center" style="font-size: 2.2em;">
              웃 음 왕
            </v-col>
          </v-row>
          <br /><br />
          <v-row>
            <v-col align="left" style="left:15%;">
              {{thisAward}}
            </v-col>
            <v-col align="right" style="right:15%">
              {{ smileKing.userId }}
            </v-col>
          </v-row>
          <br /><br />
          <v-row>
            <v-col align="center" style="left:17%;" cols="8"  class="d-none d-md-block">
              위 회원은 SMAHEAL 2020년 10월 웃음 기부에서 뛰어난 웃음 지수로 위와
              같이 수상하였기에 이 상을 수여합니다.
            </v-col>
          </v-row>
          <br /><br />
          <v-row class="d-none d-md-block">
            <v-col align="center">
              2020년 10월 1일
            </v-col>
          </v-row>
          <v-row class="d-none d-md-block">
            <v-col align="center">
              SMAHEAL 운영진
            </v-col>
          </v-row>
        </v-container>
      </v-img>
    </v-card>
    <br />
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { mapGetters } from "vuex";

export default {
  name: "AwardComp",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      smileKing: {},
      award: ["금상", "은상", "동상"],
      thisAward: "",
    };
  },
  created() {
    http
      .get(`/smile/smileKing`)
      .then(res => {
        const smileKing = res.data;
        var cnt = 0;
        for (const smile of smileKing) {
          if (smile.userId == this.getUserID) {
            this.smileKing = smile;
            this.thisAward = this.award[cnt];
          }
          cnt++;
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  computed: {
    ...mapGetters(["getUserID"])
  }
};
</script>

<style>
</style>