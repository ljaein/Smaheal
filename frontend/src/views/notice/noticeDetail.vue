<template>
  <div>
    <noticeDetailComp
    :propItem="item"
    v-if="item.length !== 0"
    />
  </div>
</template>

<script>
import noticeDetailComp from "@/components/notice/noticeDetailComp.vue";
import http from "@/util/http-common.js"

export default {
  name: "noticeDetail",
  components: {
    noticeDetailComp
  },
  data: function() {
    return {
        item: [],
    }
  },
  created() {
      http.get(`notice/find/${this.$route.params.noticeid}`)
      .then(({data}) => {
          this.item = data
      })
      .catch()
  }
};
</script>
