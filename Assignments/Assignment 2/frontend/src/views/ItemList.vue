<template>
  <v-card>
    <v-card-title>
      Book Store
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Book</v-btn>
      <v-btn @click="exportPDFB">Export PDF B</v-btn>
      <v-btn @click="exportPDFJ">Export PDF J</v-btn>
      <v-btn @click="exportCSV">Export CSV</v-btn>
      <v-btn @click="backUsers">Users</v-btn>

    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import router from "@/router";

export default {
  name: "BookList",
  components: { ItemDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    exportPDFB() {
      api.items.pdfBox();
    },
    exportPDFJ() {
      api.items.pdfJasper();
    },
    exportCSV() {
      api.items.csv();
    },

    backUsers(){
      router.push("/users")
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
