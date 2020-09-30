<template>
    <div>
      {{this.totalCnt}}
    </div>
</template>

<script>
// import searchComp from "@/components/template/searchComp.vue";
import http from "@/util/http-common.js";

export default {
    name: "templateSearch",
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