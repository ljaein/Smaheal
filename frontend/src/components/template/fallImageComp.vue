<template>
    <div>
      <v-sheet
        class="mx-auto"
        max-width="800"
      >
        <h5>
          * 넓이가 1300px 이상인 화면에서 보는 것을 추천드립니다.
        </h5>
        <v-card width="900" height="400"
          class="mx-auto" :img="require(`@/assets/template/${imgsrc}`)"
          dark
          elevation="8"
          >
          <v-slide-group
            class="pa-4"
            prev-icon="mdi-code-less-than"
            next-icon="mdi-code-greater-than"
            :show-arrows="true"
          >
            <slideComp
            v-for="(item, index) in items"
            :key="index"
            :propsitem="item"
            />
          </v-slide-group>
        </v-card>
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
import slideComp from "@/components/template/slideComp.vue";

export default {
    name: "fallImageComp",
    components: {
      slideComp,
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