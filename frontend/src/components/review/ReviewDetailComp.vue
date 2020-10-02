<template>
  <div class="container mx-auto col-xl-7 col-lg-8 col-md-10 col-sm-11 col-12 d-flex justift-content-center">
    <v-card class="mt-12 mb-3 col-xl-7 col-lg-8 col-md-10 col-sm-11 col-12">
      <!-- 이미지 -->
      <v-img :src="`${publicPath}reviewImage/${img}`" width="100%"></v-img>
      <!-- 제목 -->
      <v-card-title style="font-weight:bold" class="mt-2">{{ title }}</v-card-title>
      <!-- 내용 -->
      <v-card-text>
        <v-row class="px-3">
          <div style="width:100%; text-align:right;">
            <h4 style="display:inline-block;cursor:pointer;font-weight:bold;">by {{ nickName }}</h4>
            <p class="text-right" style="color:gray;font-weight:bold;">{{ visit }} views · {{ getFormatDate(createdAt) }}</p>
          </div>
        </v-row>
        <!-- 좋아요 -->
        <div class="mb-4 subtitle-1">
          <v-btn icon @click="upLike"><v-icon>mdi-hand-heart-outline</v-icon></v-btn>
          {{ likeCnt }}
        </div>
        <div style="font-size:1rem;font-weight:bold;letter-spacing:1px;word-spacing:2px;line-height:200%;">{{ content }}</div>
      </v-card-text>
      <!-- 버튼 -->
      <v-divider class="mx-4 mb-2"></v-divider>
      <div style="width:100%; text-align:center;">
        <v-btn class="mx-5" v-if="isWriter" @click="moveModify" icon fab large color="basil">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn class="mx-5" v-if="isWriter" @click="del = true" icon fab large color="basil">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
        <v-btn class="mx-5" @click="goBack" icon fab large color="basil">
          <v-icon>mdi-undo</v-icon>
        </v-btn>
      </div>
    </v-card>

    <v-dialog dark v-model="del" max-width="400">
      <v-card>
        <v-card-title class="headline">정말 삭제하시겠습니까?</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="(del = false), deleteReview()">확인</v-btn>
          <v-btn text @click="del = false">취소</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import moment from 'moment';

export default {
  name: 'ReviewListComp',
  data() {
    return {
      publicPath: process.env.BASE_URL,
      del: false,
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
    createdAt: { type: String },
    isWriter: { type: Boolean },
  },
  methods: {
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format('YYYY년 MM월 DD일');
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
          createdAt: this.createdAt,
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteReview() {
      http
        .delete(`/review/delete/${this.$route.params.num}`)
        .then(({ data }) => {
          if (data == 'success') {
            alert('성공적으로 삭제되었습니다.');
            this.$router.push(`/reviewList`);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    moveModify() {
      this.$router.push(`/reviewModify/${this.$route.params.num}`);
    },
    goBack() {
      window.history.back();
    },
  },
};
</script>

<style></style>
