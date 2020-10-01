<template>
    <v-container>
      <v-row>
          <v-col>
              총 {{this.totalCnt}}개의 사진에 대한 템플릿입니다.
          </v-col>
      </v-row>
      <!-- <v-row v-for="(item, index) in items" :key="index">
          <v-col>
            <v-img :src="require(`../../../public/images/${item.photo}`)"/>
          </v-col>
      </v-row> -->
      <v-row>
          <v-col>
            <fallImageComp
            :propsItem="items"
            v-if="items.length !== 0"
            />
          </v-col>
      </v-row>
    </v-container>
</template>

<script>
import fallImageComp from "@/components/template/fallImageComp.vue";
import http from "@/util/http-common.js";

export default {
    name: "templateSearch",
    components: {
        fallImageComp,
    },
    data: function() {
        return {
            keyword: "",
            items: [],
            totalCnt: 0,
        }
    },
    created() {
        this.keyword = this.$route.query.template;

        http
        .get(`/template/search/${this.keyword}`)
        .then(({data}) => {
            this.items = data;

            http.get(`/template/totalCount/${this.keyword}`)
            .then(({data}) => {
                this.totalCnt = data;
            })
        })
    }
}
</script>