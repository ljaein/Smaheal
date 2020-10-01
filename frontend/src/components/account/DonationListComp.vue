<template>
  <div class="p-3" align="center">
    <v-simple-table style="text-align:center; width:90%">
      <thead>
        <tr style="background-color:#fffbe6;">
          <td>No</td>
          <td>제목</td>
          <td>작성 날짜</td>
          <td>일련번호</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in items" :key="index">
          <td style="width:10%">{{ (page-1)*8+index + 1 }}</td>
          <td
            style="width:50%; text-overflow:ellipsis; overflow: hidden; white-space: nowrap;"
          >
            {{ item.title }}
          </td>
          <td style="width:30%">{{ getFormatDate(item.createdate) }}</td>
          <td style="width:10%;">
            <v-btn small class="green-mbtn" @click="setDonation(item)"
              >조회</v-btn
            >
          </td>
        </tr>
      </tbody>
    </v-simple-table>
    <div class="text-center m-3">
      <v-pagination
        v-model="page"
        :length="length"
        color="#356859"
        prev-icon="mdi-menu-left"
        next-icon="mdi-menu-right"
      ></v-pagination>
    </div>
    <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="400">
        <v-card>
          <v-card-title class="headline">
            Serial Number
          </v-card-title>
          <v-card-text v-if="detailItem.template">
            {{ detailItem.template }}
          </v-card-text>
          <v-card-text v-if="!detailItem.template">
            게시물이 마감되면 일련번호가 부여됩니다.
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="#356859" dark @click="dialog = false">
              확인
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common.js";

export default {
  name: "DonationList",
  props: {
    propNickname: { type: String }
  },
  data() {
    return {
      items: [],
      dialog: false,
      detailItem: [],
      page: 1,
      length: 0
    };
  },
  created() {
    this.init();
    http
      .get(`/donation/mypage/getListCnt/${this.propNickname}`)
      .then(res => {
        if (res.data % 8 == 0) {
          this.length = Math.floor(res.data / 8);
        } else {
          this.length = Math.floor(res.data / 8) + 1;
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  methods: {
    init(){
      http
      .get(`/donation/mypage/getList/${this.propNickname}/${this.page - 1}`)
      .then(({ data }) => {
        this.items = data;
      });
    },
    getFormatDate: function(regtime) {
      return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
    },
    setDonation(donation) {
      this.dialog = true;
      this.detailItem = donation;
    }
  },
  watch:{
    page(){
      this.init();
    }
  }
};
</script>