<template>
  <v-container>
    <v-row>
        <v-col>
            <v-btn text class="orange white--text">
              공지사항
            </v-btn>
        </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-spacer/>
      </v-col>
      <v-col>
        <v-autocomplete
        v-model="searchNotice"
        :search-input.sync="search"
        cache-items
        class="mx-4"
        flat
        hide-no-data
        hide-details
        label="search"
        solo-inverted
        ></v-autocomplete>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-simple-table>
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
                <td>{{item.created_at}}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-btn @click="goNoticeRegist()" text>
          글 작성하기
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

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
   };
  },
  methods: {
      goNoticeRegist: function() {
        this.$router.push('/notice/write')
      },
      goDetail: function(noticeid) {
        this.$router.push(`/notice/detail/${noticeid}`)
      }
  },
};
</script>
