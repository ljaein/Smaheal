<template>
  <div>
    <div>
      <br />
      <h1 class="text-center">{{title}}</h1>
      <div style="width:100%; text-align:right;">
        <h4 style="display: inline-block;  cursor:pointer">by {{nickName}}</h4>
      </div>
      <p class="text-right" style="color:gray">{{getFormatDate(createdAt)}}</p>
    </div>
    <div align="center">
      <v-img :src="`${publicPath}reviewImage/${img}`" width="50%"></v-img>
      <td class="text-center">{{ content }}</td>
    </div>
    <p>
      <v-btn @click="upLike">{{ likeCnt }} likes</v-btn>
      {{ visit }} views
    </p>
    <p></p>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import moment from "moment";

export default {
  name: "ReviewListComp",
  data() {
    return {
      publicPath: process.env.BASE_URL
    };
  },
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
  methods: {
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
    },
    upLike() {
      this.likeCnt += 1;
      http
        .put(`/review/update/${this.$route.params.num}`, {
          num: this.num,
          title: this.title,
          content: this.content,
          nickName: this.nickName,
          img: this.img,
          likeCnt: this.likeCnt,
          visit: this.visit,
          createdAt: this.createdAt
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>

<style>
</style>