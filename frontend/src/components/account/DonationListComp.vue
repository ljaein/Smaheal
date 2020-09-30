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
            <td class="text-center">{{item.donationid}}</td>
            <td class="text-center">{{item.title}}</td>
            <td class="text-center">{{getFormatDate(item.createdate)}}</td>
            <td class="text-center">
              <v-dialog
                v-model="dialog"
                width="500"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-btn color="#356859" dark v-bind="attrs" v-on="on">상세 보기</v-btn>
                </template>

                <v-card>
                  <v-card-title class="headline">
                    Serial Number
                  </v-card-title>
                  <v-card-text v-if="item.template">
                    {{item.template}}
                  </v-card-text>
                  <v-card-text else>
                    게시물이 마감되면 일련번호가 부여됩니다.
                  </v-card-text>
                  
                  <v-divider></v-divider>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="#356859" dark @click="dialog = false">
                      확인
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </td>
            </tr>
        </tbody>
        </template>
    </v-simple-table>
  </div>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common.js";

  export default {
    name: "DonationList",
    props: {
      propNickname: {type: String},
    },
    data () {
      return {
        items: [],
        dialog: false,
      }
    },
    created() {
        http
        .get(`/donation/mypage/getList/${this.propNickname}/0`)
        .then(({data}) => {
            this.items = data;
        });
    },
    methods: {
      getFormatDate: function(regtime) {
        return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
      }
    },
  }
</script>