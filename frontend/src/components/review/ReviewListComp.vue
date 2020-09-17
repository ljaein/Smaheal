<template>
  <div>
    <v-card :class="{ 'on-hover': hover }" width="310px" style="background: white;">
      <v-img @click="goDetail" :src="`${publicPath}reviewImage/${img}`"></v-img>
    </v-card>
    <div class="aInfo">
      <strong @click="goDetail">{{title}}</strong>
      <p >{{visit}} views · {{getFormatDate(createdAt)}}</p>
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
    }
  }
};
</script>

<style>
</style>