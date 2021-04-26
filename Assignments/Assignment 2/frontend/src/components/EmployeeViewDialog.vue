<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-form>
          <v-toolbar color="primary" dark>Sell Book</v-toolbar>
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.author" label="Author" />
          <v-text-field v-model="item.genre" label="Genre" />
          <v-text-field v-model="item.price" label="Price" />
          <v-text-field v-model="item.quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="sellItem">Sell Book</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemDialogEmployee",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    sellItem() {
      api.items
        .sellBook(this.item.id, this.item.quantity)
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
