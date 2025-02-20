# eshop

---
Nama    : Muhammad Naufal Ramadhan <br>
NPM     : 2306241700 <br>
Kelas   : B <br>

---

<details>
  <summary>
    Module 1
  </summary>

### Reflection 1
---
##### You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean   code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code. Please write your reflection inside the repository's README.md file.
---

Jawaban :
Beberapa clean code standards yang sudah saya terapkan adalah Meaningful Names, Function, dan Error Handling.
* Meaningful Names :
```
public Product findById(String Id){
  return productRepository.findById(Id);
}
```
Nama sudah menjelaskan melakukan pencarian produk berdasarkan Id.

* Function
```
public Product delete(Product product) {
  productData.remove(product);
  return product;
}
```
Funcion delete hanya melakukan 1 hal yaitu delete dengan sekecil mungkin.

* Error Handling
```
public String updateProductPost(@ModelAttribute Product updatedProduct, Model model) {
    try{
      service.update(updatedProduct);
    } catch (IllegalStateException e){
      model.addAttribute("error", "Quantity Cannot be Negative or Name cannot be empty");
      return "updateProduct";
    } catch (NoSuchElementException e) {
      model.addAttribute("error", "Product not found");
      return "updateProduct";
    }
    return "redirect:list";
}
```
Method update pada service akan throws 2 exception yang akan dihandle pada method tersebut.

Untuk Secure Coding standards yang sudah saya terapkan adalah input validation.
```
public Product update(Product updatedProduct) throws IllegalStateException, NoSuchElementException{
    if (updatedProduct.getProductQuantity() < 0 || updatedProduct.getProductName().isEmpty()){
        throw new IllegalStateException("Product quantity cannot be less than 0");
    }

    Product product = productRepository.findById(updatedProduct.getProductId());
    if (product == null) {
        throw new NoSuchElementException("Product not found");
    }

    product.setProductQuantity(updatedProduct.getProductQuantity());
    product.setProductName(updatedProduct.getProductName());
    return productRepository.update(product);
}
```
Disini dilakukan validasi input contohnya pada quantity produk dan nama di service ketika melakukan update produk, Sehingga terhindar dari value yang tidak sesuai.

Kesalahan yang saya lihat pada source code saya adalah belum ada sanitasi untuk input, sehingga memungkinkan terjadinya Injeksi script (XSS). Untuk improve nya harus dilakukan sanitasi pada backend yang akan membersihkan special character yang bisa terpakai pada command-command tertentu.

### Reflection 2
---
##### 1.  After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?
##### 2.  Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables.  What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner! Please write your reflection inside the repository's README.md file.


  ---
Jawaban :

1.  Setelah saya membuat unit test, saya jadi memikirkan mungkin case-case yang sebelumnya tidak kepikira, selain itu juga melatih saya lebih baik pada pembuatan testing. Jumlah unit test yang dibuat dalam satu class berjumlah relatif, tergantung dari ukuran classnya, tetapi target yang bisa kita kejar adalah menyentuh 100% coverage dan sudah pikirkan edge case untuk di handle. Jika kita sudah mencapai 100% coverage, itu bukan berarti code kita bebas dari bug dan error!, karena code coverage artinya dia sudah mengecek seluruh line of code dengan input yang kita buat yang akan disesuaikan dengan output yang kita harapkan, tetapi bukan berarti sudah me-handle seluruh kemungkinan, karena input dan output yang di-assert adalah yang kita buat sendiri.
2.  Jika kita melakukan prosedur yang sama dan inisiasis variabel yang sama, tentu ini akan mengurangi kualitas kebersihan kode, karena akan terjadi redundansi kode dan menambahkan yang tidak perlu. Selain itu, akan susah juga untuk me-maintain code nya, karena semua kode mengikuti set-up yang sama, jika ada perubahan maka perlu diganti juga di semua code yang lain.
</details>

<details>
  <summary>
    Module 2
  </summary>

### Reflection
1.  List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
2.  Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

---
Jawaban :
1.  Beberapa kesalahan kualitas kode yang sudah saya jumpai sebagai berikut :
* Unused Import
* Improper Naming Convention
* Not Declaring final in unassigned Parameter and local Variables
* Not Implementing Only One Exit Point
* Unnecessary Constructor
* Too Short for Naming Variables
* Unnecessary Public modifier in interface <br>

  Strategi yang saya gunakan untuk fix beberapa kesalahan kode tersebut dengan melakukan code analysis checking menggunakan github actions PMD, dan melihat hasil dari analysisnya pada sarif file yang di upload sebagai artifacts di github action. Kemudian saya lihat log detailnya untuk menemukan lokasi kesalahan dari kode yang bersangkutan.

2. workflow pada repo ini sudah mencakup Serangkaian dari CI (Continuous Integration) seperti Testing (Unit test) menggunakan Junit ketika kita melakukan pull request atau push ke seluruh branch, lalu Code Analysis menggunakan ScoreCard untuk branch master dan juga PMD untuk seluruh branch, selain itu workflowsnya juga lakukan upload artifacts untuk loggin atau hasil dari code analysis. Untuk Continuous Deployment (CD) juga sudah memenuhi dengan mengimplementasikan layanan koyeb yang akan me-track changes di branch master saya ketika ada perubahan dan akan lakukan auto deployment. Sehingga menurut saya pengaplikasian beberapa jobs pada workflow saya sudah bisa memenuhi definisi Continuous Integration and Continuous Deployment, karena sudah secara otomatatis dam berkelajutan melakukan integrasi dan deployment ketika terdapat perubahan
</details>
