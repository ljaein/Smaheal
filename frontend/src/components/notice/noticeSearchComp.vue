<template>
<div class="col-md-12 p-0">
    <div class="n_header_img d-flex justify-content-center">
      <div
        style="color:white; position:absolute; top:200px; font-size:3rem;"
      >
        공지사항
     </div>
    </div>
    <div
      class="d-flex justify-content-center"
      style="background-color:rgb(188,150,39)"
    >
  <v-container class="col-md-11">
    <v-card color="#fffbe6">
      <!-- <v-row style="color:#356859;font-size:1.3rem;" class="p-4">
        
        <span class="pl-5" style="display:inline-flex;vertical-align:middle"><v-icon class="mr-1" color="#356859"
          >mdi-clipboard-alert-outline</v-icon
        >공지사항</span>
      </v-row> -->
      <v-row>
        <v-col>
          <v-spacer />
        </v-col>
        <v-col class="mr-2">
          <v-form action="/notice/search" @submit="checkForm">
            <v-row class="mr-1">
              <v-text-field
              hide-details
                placeholder="제목으로 검색됩니다.. 검색어를 입력하세요."
                outlined
                color="#356859"
                clearable
                v-model="title"
                ref="search"
                name="title"
                prepend-inner-icon="mdi-text-search"
              />
            </v-row>
          </v-form>
        </v-col>
      </v-row>
    </v-card>
    <v-row>
      <v-col class="pt-0">
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-center">No</th>
                <th class="text-center">제목</th>
                <th class="text-center">작성자</th>
                <th class="text-center">작성 날짜</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in items" :key="index">
                <td class="text-center">{{item.noticeid}}</td>
                <td class="text-center" style="cursor:pointer;" @click="goDetail(item.noticeid)">{{item.title}}</td>
                <td class="text-center">관리자</td>
                <td class="text-center">{{getFormatDate(item.createdAt)}}</td>
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
      color="#356859"
      />
    </div>
    <v-row>
        <v-col>
            <v-btn @click="goBack()" class="green-mbtn">
                전체 목록보기
            </v-btn>
        </v-col>
      <v-col class="text-right">
        <v-btn @click="goNoticeRegist()" color="#356859" dark v-if="getUserID == 'admin'">
        글 작성하기
      </v-btn>
      </v-col>
    </v-row>
  </v-container>
    </div>
</div>
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
     title: "",
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
      },
      checkForm: function(e) {
        if (this.title !== "") {
          return true;
        }
        e.preventDefault();
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
