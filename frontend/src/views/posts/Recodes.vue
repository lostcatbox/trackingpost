<template>
  <div class="board-detail">
    <div class="common-buttons">
      <b-button type="button" pill v-on:click="fnPostDetails">이전페이지로</b-button>&nbsp;
    </div>
    <br>
    <b-table-simple class="w3-table-all">
      <thead>
      <b-tr>
        <b-th>택배회사</b-th>
        <b-th>운송장 번호</b-th>
        <b-th>보내는이</b-th>
        <b-th>받는이</b-th>
        <b-th>택배종류</b-th>
        <b-th>상세정보</b-th>
        <b-th>현위치</b-th>
        <b-th>택배회사업데이트일</b-th>
        <b-th>최근 업데이트 시간</b-th>
      </b-tr>
      </thead>
      <tbody>
      <tr v-for="(row, idx) in list" :key="idx">
        <b-th>{{ row.postCompany }}</b-th>
        <b-th>{{ row.postNumber }}</b-th>
        <b-th>{{ row.sender }}</b-th>
        <b-th>{{ row.receiver }}</b-th>
        <b-th>{{ row.contentType }}</b-th>
        <b-th>{{ row.message }}</b-th>
        <b-th>{{ row.location }}</b-th>
        <b-th>{{ row.statusData}}</b-th>
        <b-th>{{ row.modifiedDate }}</b-th>
      </tr>
      </tbody>
    </b-table-simple>
    <div class="common-buttons">
      <b-button type="button" pill v-on:click="fnPostDetails">이전페이지로</b-button>&nbsp;
    </div>
  </div>
</template>

<script>
export default {
  name: "Recodes",
  components:{
  },
  data(){
    return{
      userId: this.$route.query.userId, //PostDetail.vue가 query로 넘겨준 데이터
      list: {}, //리스트 데이터
    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.requestBody = { // 데이터 전송
      }
      this.$axios.get(this.$homeserviceAPIUrl + "/"+this.userId+"/", {
      }).then((res) => {
        console.log(res.data);

        this.list = res.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.

      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnPostDetails(){
      this.$router.go(-1)
    }
  },
}
</script>