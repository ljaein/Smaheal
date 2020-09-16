<template>
  <div>
    <noticeListComp
    :propItems="items"
    v-if="items.length !== 0"
    />
  </div>
</template>

<script>
import noticeListComp from "@/components/notice/noticeListComp.vue";
import http from "@/util/http-common.js";

export default {
  name: "noticeList",
  components: {
    noticeListComp
  },
  data: function() {
    return {
      items: [],
    }
  },
  created() {
    http
    .get('/notice/findAll')
    .then(({data}) => {
      this.items = data;
    })
  }
};
</script>
