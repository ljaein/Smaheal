<template>
  <v-container class="pa-0">
    <v-sheet class="pa-6 mt-4" color="#fffbe6">
      <v-icon class="mr-2" color="#356859">mdi-clipboard-alert-outline</v-icon>
      공지사항
    <v-row>
      <v-col>
        <v-spacer/>
      </v-col>
      <v-col>
        <v-form action="/notice/search">
          <v-text-field
            placeholder="제목으로 검색됩니다. 검색어를 입력하세요."
            outlined
            color="#356859"
            clearable
            name="title"
            prepend-inner-icon="mdi-text-search"
          />
        </v-form>
      </v-col>
    </v-row>
    </v-sheet>
    <v-row>
      <v-col>
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
                <td class="text-center" @click="goDetail(item.noticeid)">{{item.title}}</td>
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
      <v-col class="text-right">
        <v-btn @click="goNoticeRegist()" color="#356859" dark v-if="getUserID == 'admin'">
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
  name: "noticeListComp",
  props: {
    propItems: {type: Array},
  },
  data(){
   return {
     items: this.propItems,
     previousPage: 0,
     currentPage: 1,
     totalPage: this.propCount,
     visibleCnt: 5,
     limit: 0,
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
  watch: {
    currentPage: function(currentPage) {
      this.previousPage = currentPage - 1;
      this.limit = this.previousPage * 6;

      http.get(`/notice/findAll/${this.limit}`)
      .then(({data}) => {
        this.items = data;
      })
      .catch(e => {
        console.log(e);
      })
    },
  },
  created() {
    http
    .get('/notice/getCount')
    .then(({data}) => {
      if (data % 6 == 0) {
        this.totalPage = Math.floor((data) / 6);
      } else {
        this.totalPage = Math.floor((data) / 6) + 1;
      }
    });
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
};
</script>
