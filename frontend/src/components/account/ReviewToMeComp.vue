<template>
  <div class="my-3">
    <v-carousel hide-delimiters light style="box-shadow: 0px 0px; height:400px;" class=" py-5">
      <v-carousel-item  class="my-auto" v-for="(reviews, index) in reviewList" :key="index">
        <v-layout row class="px-2">
          <v-flex
            class="col-12 col-sm-6 col-md-3"
            v-for="(review, index2) in reviews"
            :key="index2"
          >
            <v-card
              class="rounded-lg"
              max-width="400px"
              style="overflow:hidden;"
            >
              <div style="height:200px;width:100%;overflow:hidden;">
                <img
                  class="review-img"
                  :src="`${publicPath}reviewImage/${review.img}`"
                />
              </div>
              <v-card-text style="font-weight:bold;" class="py-1">
                <div style="font-size:1.1rem;">{{ review.nickName }}</div>
                <div class="d-flex justify-content-between mt-1">
                  <div style="font-size:1rem;" class="d-flex">
                    <div class="detail-title">
                      <v-icon class="mr-1" style="font-size:1.2rem;color:#f25278"
                        >mdi-thumb-up</v-icon
                      ><span style="color:#f25278;">{{ review.likeCnt }}</span>
                    </div>
                    <div class="ml-4 detail-title">
                      <v-icon class="mr-1" style="font-size:1.2rem;color:#151757"
                        >mdi-eye</v-icon
                      ><span style="color:#151757;">{{ review.visit }}</span>
                    </div>
                  </div>
                  <div align="right">{{ review.createdAt.split("T")[0] }}</div>
                </div>
              </v-card-text>
              <v-divider class="mx-2 my-0"></v-divider>
              <v-card-text class="pt-1 pb-2" align="center">
                <p
                  class="mb-1"
                  style="font-size:1rem; font-weight:bold; text-overflow:ellipsis; overflow: hidden; white-space: nowrap;"
                >
                  {{ review.title }}
                </p>
                <v-btn
                  small
                  class="green-mbtn m-1"
                  @click="goDetail(review.num)"
                  >리뷰보기</v-btn
                >
              </v-card-text>
            </v-card>
          </v-flex>
        </v-layout>
      </v-carousel-item>
    </v-carousel>

    <!-- <div
      class="col-12 col-sm-6 col-md-3"
      v-for="(review, index) in reviewList"
      :key="index"
    >
      <v-card class="rounded-lg" max-width="400px" style="overflow:hidden;">
        <div style="height:200px;width:100%;overflow:hidden;">
          <img
            class="review-img"
            :src="`${publicPath}reviewImage/${review.img}`"
          />
        </div>
        <v-card-text style="font-weight:bold;" class="py-1">
          <div style="font-size:1.1rem;">{{ review.nickName }}</div>
          <div class="d-flex justify-content-between mt-1">
            <div style="font-size:1rem;" class="d-flex">
              <div class="detail-title">
                <v-icon class="mr-1" style="font-size:1.2rem;"
                  >mdi-thumb-up</v-icon
                >{{ review.likeCnt }}
              </div>
              <div class="ml-4 detail-title">
                <v-icon class="mr-1" style="font-size:1.2rem;">mdi-eye</v-icon
                >{{ review.visit }}
              </div>
            </div>
            <div align="right">{{ review.createdAt.split("T")[0] }}</div>
          </div>
        </v-card-text>
        <v-divider class="mx-2 my-0"></v-divider>
        <v-card-text class="pt-1 pb-2" align="center">
          <p class="mb-1" style="font-size:1rem; font-weight:bold; text-overflow:ellipsis; overflow: hidden; white-space: nowrap;">{{ review.title }}</p>
          <v-btn small class="green-mbtn m-1" @click="goDetail(review.num)">리뷰보기</v-btn>
        </v-card-text>
      </v-card>
    </div> -->
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapGetters, mapState } from "vuex";
export default {
  name: "ReviewToMeComp",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      reviewList: []
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      http
        .get(`/review/getReviewToMe/${this.getUserID}`)
        .then(res => {
          this.reviewList = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    goDetail(num) {
      this.$router.push(`/reviewDetail/${num}`);
    }
  },
  computed: {
    ...mapGetters(["getProfile", "getUserID"]),
    ...mapState({
      uname: state => `${state.user.getProfile}`,
      userID: state => `${state.user.getUserID}`
    })
  }
};
</script>
<style>
.review-img {
  height: 200px !important;
  width: 100% !important;
  /* object-fit: cover; */
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}
.detail-title {
  font-size: 1rem;
  margin: 0;
  vertical-align: middle;
  display: inline-flex;
}
</style>