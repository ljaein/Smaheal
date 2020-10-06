<template>
  <div class="d-flex justify-content-center">
    <noticeSearchComp
    :propItems="items"
    :propCount="totalCnt"
    :propKeyword="keyword"
    v-if="items.length !== 0 && totalCnt !== 0"
    />
    <searchErrorComp
    :propKeyword="keyword"
    v-else/>
  </div>
</template>

<script>
import noticeSearchComp from "@/components/notice/noticeSearchComp.vue";
import searchErrorComp from "@/components/notice/searchErrorComp.vue";
import http from "@/util/http-common.js";

export default {
  name: "noticeSearch",
  components: {
    noticeSearchComp,
    searchErrorComp
  },
  data: function() {
    return {
        keyword: "",
        items: [],
        totalCnt: 0,
    }
  },
  created() {
    this.keyword = this.$route.query.title;

    http
    .get(`/notice/search/${this.keyword}/0`)
    .then(({data}) => {
      this.items = data;

      http.get(`/notice/search/getCount/${this.keyword}`)
      .then(({data}) => {
        this.totalCnt = data;
      })
    })
  }
};
</script>
