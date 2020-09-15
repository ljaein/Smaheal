<template>
  <div>
    <noticeListComp/>
  </div>
</template>

<script>
// @ is an alias to /src
import noticeListComp from "@/components/notice/noticeListComp.vue";

export default {
  name: "noticeList",
  components: {
    noticeListComp
  }
};
</script>
