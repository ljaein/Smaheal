<template>
  <div>
    <noticeSearchComp
    :propItems="items"
    v-if="items.length !== 0"
    />
  </div>
</template>

<script>
import noticeSearchComp from "@/components/notice/noticeSearchComp.vue";
import http from "@/util/http-common.js";

export default {
  name: "noticeSearch",
  components: {
    noticeSearchComp
  },
  data: function() {
    return {
        keyword: "",
        items: [],
    }
  },
  created() {
    this.keyword = this.$route.query.title;

    http
    .get(`/notice/search/${this.keyword}`)
    .then(({data}) => {
    this.items = data;
    })
  }
};
</script>
