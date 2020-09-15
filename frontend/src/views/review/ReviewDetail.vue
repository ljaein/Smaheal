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
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import ReviewDetailComp from "@/components/review/ReviewDetailComp.vue";

export default {
  name: "ReviewDetail",
  components: {
    ReviewDetailComp
  },
  data() {
    return {
      item: {}
    };
  },
  created() {
    http
      .get(`/review/get/${this.$route.params.num}`)
      .then(({ data }) => {
        this.item = data;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>

<style>
</style>