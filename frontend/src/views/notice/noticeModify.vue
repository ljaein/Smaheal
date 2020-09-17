<template>
  <div>
    <noticeModifyComp
    :propItems="this.items"
    v-if="this.items.length !== 0"
    />
  </div>
</template>

<script>
import noticeModifyComp from "@/components/notice/noticeModifyComp.vue";
import http from "@/util/http-common";

export default {
  name: "noticeModify",
  components: {
    noticeModifyComp
  },
  data: function() {
    return {
        items: [],
    }
  },
  created() {
      http
      .get(`/notice/find/${this.$route.params.noticeid}`)
      .then(({data}) => {
        this.items = data;
      })
  }
};
</script>
