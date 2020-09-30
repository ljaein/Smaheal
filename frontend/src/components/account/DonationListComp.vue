<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">No</th>
            <th class="text-center">제목</th>
            <th class="text-center">작성 날짜</th>
            <th class="text-center">일련번호</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in items" :key="index">
            <td class="text-center">{{ index+1 }}</td>
            <td class="text-center">{{ item.title }}</td>
            <td class="text-center">{{ getFormatDate(item.createdate) }}</td>
            <td class="text-center"> <v-btn small class ="green-mbtn" @click="setDonation(item)">조회</v-btn> </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
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
      detailItem:[]
    };
  },
  created() {
    http
      .get(`/donation/mypage/getList/${this.propNickname}/0`)
      .then(({ data }) => {
        this.items = data;
      });
  },
  methods: {
    getFormatDate: function(regtime) {
      return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
    },
    setDonation(donation){
      this.dialog = true;
      this.detailItem = donation;
    }
  }
};
</script>