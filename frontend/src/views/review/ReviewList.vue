<template>
  <div>
    <v-container fluid class="mt-5 pl-15 pr-15">
      <div style="text-align:right;">
        <v-btn v-if="getProfile" class="green-mbtn" @click="goReviewWrite" outlined>후기 등록</v-btn>
      </div>
      <v-row>
        <div class="col-12 col-sm-6 col-md-3" v-for="(item, index) in items" :key="index">
          <ReviewListComp
            :num="item.num"
            :nickName="item.nickName"
            :title="item.title"
            :content="item.content"
            :img="item.img"
            :likeCnt="item.likeCnt"
            :visit="item.visit"
            :createdAt="item.createdAt"
          />
        </div>
      </v-row>
    </v-container>
    <v-pagination v-model="page" :length="length" :page="page" :total-visible="totalVisible"></v-pagination>
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