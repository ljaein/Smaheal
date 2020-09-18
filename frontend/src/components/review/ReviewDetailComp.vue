<template>
  <div>
    <v-container fluid>
      <v-row justify="space-around">
        <v-col cols="12" md="8">
          <br />
          <h1 class="text-center">{{title}}</h1>
          <div style="width:100%; text-align:right;">
            <h4 style="display: inline-block;  cursor:pointer">by {{nickName}}</h4>
          </div>
          <p class="text-right" style="color:gray">{{ visit }} views · {{getFormatDate(createdAt)}}</p>
        </v-col>
      </v-row>
      <v-row justify="space-around">
        <v-col cols="12" md="8">
          <v-img :src="`${publicPath}reviewImage/${img}`" width="100%"></v-img>
          <v-textarea auto-grow v-model="content" readonly solo flat></v-textarea>
        </v-col>
      </v-row>
      <v-row justify="space-around">
        <v-col cols="12" md="8">
          <v-btn @click="upLike" outlined>{{ likeCnt }} likes</v-btn>
        </v-col>
      </v-row>
    </v-container>
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