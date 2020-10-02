<template>
  <div class="d-flex justify-content-center">
    <DonationRequestDetailComp/>
  </div>
</template>

<script>
// @ is an alias to /src
import DonationRequestDetailComp from "@/components/donation/DonationRequestDetailComp.vue";

export default {
  name: "DonationRequestDetail",
  components: {
    DonationRequestDetailComp
  }
};
</script>
