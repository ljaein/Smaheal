<template>
    <v-container v-if="this.totalCnt != 0">
      <v-row>
          <v-col>
              총 {{this.totalCnt}}개의 사진에 대한 결과입니다.
          </v-col>
      </v-row>
      <v-row>
          <v-col>
            <fallImageComp
            :propsItem="items"
            v-if="items.length !== 0"
            />
          </v-col>
      </v-row>
    </v-container>
    <v-container v-else style="text-align: center;">
        <v-row>
            <v-col class="h1 font-weight-bold"> (*ㅠㅅㅠ*) </v-col>
        </v-row>
        <v-row>
            <v-col>기부된 사진이 없습니다.</v-col>
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