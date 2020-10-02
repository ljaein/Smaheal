<template>
  <div>
    <div class="container d-flex justify-content-center col-12">
      <ReviewDetailComp
        :num="item.num"
        :nickName="item.nickName"
        :title="item.title"
        :content="item.content"
        :img="item.img"
        :likeCnt="item.likeCnt"
        :visit="item.visit"
        :createdAt="item.createdAt"
        :isWriter="isWriter"
      />
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import ReviewDetailComp from '@/components/review/ReviewDetailComp.vue';
import { mapGetters } from 'vuex';

export default {
  name: 'ReviewDetail',
  components: {
    ReviewDetailComp,
  },
  data() {
    return {
      item: {},
      isWriter: false,
    };
  },
  created() {
    http
      .get(`/review/get/${this.$route.params.num}`)
      .then(({ data }) => {
        this.item = data;
        if (this.getProfile == this.item.nickName) {
          this.isWriter = true;
        }
      })
      .catch((err) => {
        console.log(err);
      });
  },
  computed: {
    ...mapGetters(['getProfile']),
  },
  methods: {
    
  },
};
</script>

<style></style>
