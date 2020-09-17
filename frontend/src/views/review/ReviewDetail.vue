<template>
  <div>
    <ReviewDetailComp
      :num="item.num"
      :nickName="item.nickName"
      :title="item.title"
      :content="item.content"
      :img="item.img"
      :likeCnt="item.likeCnt"
      :visit="item.visit"
      :createdAt="item.createdAt"
    />
    <div v-if="isWriter" style="width:100%; text-align:center;">
      <v-btn @click="moveModify" outlined>수정</v-btn>
      <v-btn @click="deleteReview" outlined>삭제</v-btn>
      <v-btn @click="goBack" outlined>뒤로가기</v-btn>
    </div>
    <br>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import ReviewDetailComp from "@/components/review/ReviewDetailComp.vue";
import { mapGetters } from "vuex";

export default {
  name: "ReviewDetail",
  components: {
    ReviewDetailComp
  },
  data() {
    return {
      item: {},
      isWriter: false
    };
  },
  created() {
    http
      .get(`/review/get/${this.$route.params.num}`)
      .then(({ data }) => {
        this.item = data;
        if (this.getProfile == this.item.nickName) {
          this.isWriter = true;
        } else {
          this.item.visit += 1;
          http
            .put(`/review/update/${this.$route.params.num}`, {
              num: this.item.num,
              title: this.item.title,
              content: this.item.content,
              nickName: this.item.nickName,
              img: this.item.img,
              likeCnt: this.item.likeCnt,
              visit: this.item.visit,
              createdAt: this.item.createdAt
            })
            .catch(err => {
              console.log(err);
            });
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  computed: {
    ...mapGetters(["getProfile"])
  },
  methods: {
    deleteReview() {
      http
        .delete(`/review/delete/${this.$route.params.num}`)
        .then(({ data }) => {
          if (data == "success") {
            alert("성공적으로 삭제되었습니다.");
            this.$router.push(`/reviewList`);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    moveModify() {
      this.$router.push(`/reviewModify/${this.$route.params.num}`);
    },
    goBack() {
      this.$router.push(`/reviewList`);
    }
  }
};
</script>

<style>
</style>