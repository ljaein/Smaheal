<template>
  <div>
    <br />
    <h1 align="center">후기 게시판</h1>
    <v-container fluid>
      <v-row>
        <v-col cols="12">
          <v-row align="stretch" justify="space-around">
            <ReviewListComp
              v-for="(item, index) in items"
              :key="index"
              :num="item.num"
              :nickName="item.nickName"
              :title="item.title"
              :content="item.content"
              :img="item.img"
              :likeCnt="item.likeCnt"
              :visit="item.visit"
              :createdAt="item.createdAt"
            />
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <v-pagination v-model="page" :length="length" :page="page" :total-visible="totalVisible"></v-pagination>
    <div style="text-align:center;">
      <v-btn v-if="getProfile" @click="goReviewWrite" outlined>후기 등록</v-btn>
    </div>
    <br>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import ReviewListComp from "@/components/review/ReviewListComp.vue";
import { mapGetters } from "vuex";

export default {
  name: "ReviewList",
  data() {
    return {
      items: [],
      page: 1,
      length: 0,
      totalVisible: 10
    };
  },
  components: {
    ReviewListComp
  },
  created() {
    http
      .get(`/review/getList/0`)
      .then(({ data }) => {
        this.items = data;
      })
      .catch(err => {
        console.log(err);
      });
    http
      .get(`/review/getListCnt`)
      .then(({ data }) => {
        if (data % 8 == 0) {
          this.length = Math.floor(data / 8);
        } else {
          this.length = Math.floor(data / 8) + 1;
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
    goReviewWrite() {
      this.$router.push(`/reviewWrite`);
    }
  },
  watch: {
    page(page) {
      page = (page - 1) * 8;
      http
        .get(`/review/getList/${page}`)
        .then(({ data }) => {
          this.items = data;
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