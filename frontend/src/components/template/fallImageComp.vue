<template>
    <div>
      <v-sheet
        class="mx-auto"
        max-width="800"
      >
        <v-timeline align-top
        :dense="$vuetify.breakpoint.smAndDown"
        >
          <ebookComp
          :propsitem="items"
          />
        </v-timeline>
        <v-row>
          <v-col class="text-right">
            <v-btn color="#356859" dark @click="goWriteReview">
              후기 남기기
            </v-btn>
          </v-col>
        </v-row>
      </v-sheet>
    </div>
</template>

<script>
// import slideComp from "@/components/template/slideComp.vue";
import ebookComp from "@/components/template/ebookComp.vue";

export default {
    name: "fallImageComp",
    components: {
      // slideComp,
      ebookComp,
    },
    props: {
      propsItem: {type: Array}
    },
    data: function() {
        return {
          items: [],
          imgsrc: "fall-background.jpg",
          num: 0,
          donationid: "",
        }
    },
    methods: {
      fetchData: function() {
        this.items = this.propsItem;
        this.donationid = this.items[0].donationid;
      },
      random: function() {
        this.num = Math.floor(Math.random() * 4);

        if (this.num == 0) {
          this.imgsrc = "flower-background.jpg"
        } else if (this.num == 1) {
          this.imgsrc = "night-background.jpg"
        } else if (this.num == 2) {
          this.imgsrc = "seascape-background.jpg"
        }
      },
      goWriteReview(){
        this.$router.push(`/reviewWrite/${this.donationid}`);
      },
    },
    created() {
      this.fetchData();
      this.random();
    },
}
</script>