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
        <v-form action="/notice/search">
          <v-text-field
            placeholder="제목으로 검색됩니다. 검색어를 입력하세요."
            outlined
            color="black"
            clearable
            name="title"
            prepend-inner-icon="mdi-text-search"
          />
        </v-form>
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
        <v-col>
            <v-btn @click="goBack()" outlined>
                전체 목록보기
            </v-btn>
        </v-col>
      <v-col class="text-right">
        <v-btn @click="goNoticeRegist()" outlined v-if="getUserID == 'admin'">
        글 작성하기
      </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common.js";
import { mapGetters, mapState } from "vuex";

export default {
  name: "noticeSearchComp",
  props: {
    propItems: {type: Array},
    propCount: {type: Number},
    propKeyword: {type: String},
  },
  data(){
   return {
     items: this.propItems,
     previousPage: 0,
     currentPage: 1,
     dataPerPage: 6,
     totalPage: this.propCount,
     visibleCnt: 5,
     limit: 0,
     keyword: this.propKeyword,
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
      },
      goBack: function() {
          this.$router.push('/notice').catch(() => {})
      }
  },
  computed: {
    ...mapGetters([
      "isAuthenticated",
      "isProfileLoaded",
      "getProfile",
      "getRealName",
      "getUserNum",
      "getUserID",
      "getUserBirth",
    ]),
    ...mapState({
      authLoading: state => state.auth.status === "loading",
      uname: state => `${state.user.getProfile}`,
      userNum: state => `${state.user.getUserNum}`,
      userName: state => `${state.user.getRealName}`,
      userID: state => `${state.user.getUserID}`,
      userBirth: state => `${state.user.getUserBirth}`,
    }),
  },
  watch: {
    currentPage: function(currentPage) {
      this.previousPage = currentPage - 1;
      this.limit = this.previousPage * 6;

      http.get(`/notice/search/${this.keyword}/${this.limit}`)
      .then(({data}) => {
        this.items = data;
      })
      .catch(e => {
        console.log(e);
      })
    },
  },
  created() {
    if (this.propCount % 6 == 0) {
      this.totalPage = Math.floor((this.propCount) / 6);
    } else {
      this.totalPage = Math.floor((this.propCount) / 6) + 1;
    }
  },
};
</script>
