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
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "Books_Out_Of_Stock_PDFBox.pdf");
      document.body.appendChild(link);
      link.click();
    });
  },
  pdfJasper() {
    return HTTP.get(BASE_URL + "/books/export/PDF_JASPER", {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "Books_Out_Of_Stock_PDFJasper.pdf");
      document.body.appendChild(link);
      link.click();
    });
  },
  csv() {
    return HTTP.get(BASE_URL + "/books/export/CSV", {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "Books_Out_Of_Stock.csv");
      document.body.appendChild(link);
      link.click();
    });
  },
  sellBook(id, quantity) {
    return HTTP.patch(
      BASE_URL + "/books/sell-book/" + id + "/" + quantity,
      {},
      {
        headers: authHeader(),
      }
    ).then((response) => {
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
