<template>
  <div>
      <h1 align="center">후기 게시판</h1>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">num</th>
            <th class="text-center">title</th>
            <th class="text-center">nickName</th>
            <th class="text-center">likeCnt</th>
            <th class="text-center">visit</th>
            <th class="text-center">createdAt</th>
          </tr>
        </thead>
        <tbody>
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
        </tbody>
      </template>
    </v-simple-table>
    <v-btn v-if="getProfile" @click="goReviewWrite">후기 등록</v-btn>
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
      items: []
    };
  },
  components: {
    ReviewListComp
  },
  created() {
    http
      .get(`/review/getList`)
      .then(({ data }) => {
        this.items = data;
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
  }
};
</script>

<style>
</style>