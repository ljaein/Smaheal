<template>
  <v-container class="pa-0">
    <v-sheet class="pa-6 mt-4" dark>
      <v-icon class="mr-2">mdi-clipboard-alert-outline</v-icon>
      공지사항
    </v-sheet>
    <v-row>
      <v-col>
        <v-spacer/>
      </v-col>
      <v-col>
        <v-autocomplete
        v-model="searchNotice"
        cache-items
        class="mx-4 ml-16"
        flat
        hide-no-data
        hide-details
        label="제목으로 검색됩니다. 검색어를 입력하세요."
        solo-inverted
        ></v-autocomplete>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-simple-table dark>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">No</th>
                <th class="text-left">제목</th>
                <th class="text-left">작성자</th>
                <th class="text-left">작성 날짜</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in items" :key="index">
                <td>{{item.noticeid}}</td>
                <td @click="goDetail(item.noticeid)">{{item.title}}</td>
                <td>관리자</td>
                <td>{{getFormatDate(item.createdAt)}}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>
    <div class="text-center">
      <v-pagination
      v-model="currentPage"
      :length="totalPage"
      :total-visible="visibleCnt"
      color="black"
      />
    </div>
    <v-row>
      <v-col class="text-right">
        <v-btn @click="goNoticeRegist()" outlined>
        글 작성하기
      </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import moment from "moment";

export default {
  name: "noticeListComp",
  props: {
    propItems: {type: Array},
  },
  data(){
   return {
     items: this.propItems,
     searchNotice: "",
     search: "",
     currentPage: 1,
     dataPerPage: 7,
     totalPage: 5,
     visibleCnt: 5,
   };
  },
  methods: {
      goNoticeRegist: function() {
        this.$router.push('/notice/write')
      },
      goDetail: function(noticeid) {
        this.$router.push(`/notice/detail/${noticeid}`)
      },
      getFormatDate: function(regtime) {
        return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
      }
  },
};
</script>
