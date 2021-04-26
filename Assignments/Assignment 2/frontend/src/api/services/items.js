import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/books", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/books", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    return HTTP.put(BASE_URL + "/books/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteBook(id) {
    return HTTP.delete(BASE_URL + "/books/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  pdfBox() {
    return HTTP.get(BASE_URL + "/books/export/PDF_BOX", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  pdfJasper() {
    return HTTP.get(BASE_URL + "/books/export/PDF_JASPER", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  csv() {
    return HTTP.get(BASE_URL + "/books/export/CSV", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sellBook(id, quantity) {
    return HTTP.patch(BASE_URL + "/books/sell/" + id, quantity, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  searchBook(search) {
    return HTTP.get(BASE_URL + "/books/search-books/" + search, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
