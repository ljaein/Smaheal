<template>
  <div>
    <v-card width="310px" style="background: white;">
      <v-img @click="goDetail" :src="`${publicPath}reviewImage/${img}`"></v-img>
    </v-card>
    <div class="aInfo">
      <strong @click="goDetail">{{title}}</strong>
      <p>{{visit}} views · {{setTime()}}</p>
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "ReviewListComp",
  props: {
    num: { type: Number },
    nickName: { type: String },
    title: { type: String },
    content: { type: String },
    img: { type: String },
    likeCnt: { type: Number },
    visit: { type: Number },
    createdAt: { type: String }
  },
  data() {
    return {
      publicPath: process.env.BASE_URL
    };
  },
  methods: {
    goDetail() {
      this.$router.push(`/reviewDetail/${this.num}`);
    },
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
    },
    setTime: function() {
      let today = new Date();
      let timeValue = new Date(
        Number(this.createdAt.slice(0, 4)),
        Number(this.createdAt.slice(5, 7) - 1),
        Number(this.createdAt.slice(8, 10)),
        Number(this.createdAt.slice(11, 13)),
        Number(this.createdAt.slice(14, 16)),
        Number(this.createdAt.slice(17, 19)),
        0
      );

      timeValue.setHours(timeValue.getHours() + 9);
      let betweenTime = Math.floor(
        (today.getTime() - timeValue.getTime()) / 1000 / 60
      );
      if (betweenTime < 1) return "방금전";
      if (betweenTime < 60) {
        return `${betweenTime}분전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
      }
      return this.createdAt;
    }
  }
};
</script>

<style>
</style>