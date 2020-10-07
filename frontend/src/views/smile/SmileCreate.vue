<template>
  <div>
    <div class="d_header_img justify-content-center d-none d-md-block"></div>
    <SmileCreateComp/>
    <div class="py-10" color="black"></div>
  </div>
</template>

<script>
import SmileCreateComp from "@/components/smile/SmileCreateComp.vue";

export default {
  name: "SmileCreate",
  components: {
    SmileCreateComp
  }
};
</script>
