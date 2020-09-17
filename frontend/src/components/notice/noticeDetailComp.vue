<template>
  <v-container class="pa-0">
    <v-sheet class="pa-6 mt-4 mb-5" dark>
      <v-icon class="mr-2">mdi-clipboard-alert-outline</v-icon>
      공지사항
    </v-sheet>
    <v-sheet dark class="pa-5 mb-5">
      <div class="headline pa-7">
        {{this.item.title}}
      </div>
      <div class="text-right pb-5">
        {{getFormatDate(this.item.createdAt)}}
      </div>
      <div class="pa-7">
          {{this.item.content}}
      </div>
    </v-sheet>
    <v-row>
      <v-col class="text-right">
            <v-btn class="mr-5" @click="goBack()" outlined>
                목록으로 가기
            </v-btn>
        </v-col>
    </v-row>
  </v-container>
</template>

<script>
import moment from "moment";

export default {
  name: "noticeDetailComp",
  props: {
      propItem: {type: Object}
  },
  data(){
   return {
       item: this.propItem,
   };
  },
  methods: {
    goBack: function() {
      this.$router.push('/notice')
    },
    getFormatDate: function(regtime) {
      return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
    },
  },
};
</script>
