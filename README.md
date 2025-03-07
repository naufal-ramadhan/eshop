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

<details>
  <summary>
    Module 3
  </summary>

### Reflection <br>
1  Explain what principles you apply to your project! <br>
2  Explain the advantages of applying SOLID principles to your project with examples. <br>
3  Explain the disadvantages of not applying SOLID principles to your project with examples. <br>
--- <br>

Jawaban  : <br>
1.  Explain what principles you apply to your project! <br>
    1).  Have you Implemented SRP? <br>
    Sudah, dimana sebelumnya carController digabung di dalam productController, SRP (Single Responsibility Principle) yang berarti satu class dibuat dengan 1 tanggung jawab yang jelas. Sekarang carController dipisah dari productController dan tidak me-extend kelas productController lagi, sehingga sekarang lebih jelas. <br>
    2). Have you Implement OCP? <br>
    Sudah, Sebelumnya carRepository merupakan langsung implementation, sekarang saya buat carRepository memnjadi sebuah interface, dan implementationnya akan implement dari interface carRepository, OCP (Open Close Principle) adalah prinsip dimana kita bisa melakukan ekstensi tetapi tidak memodif behaviour. Dengan menggunakan interface, semisal kita ingin melakukan ekstensi kita bisa melakukannya dengan class baru dengan implement carRepository nya, sehingga tidak mengubah behaviour lama dengan fungsi tambahan (ekstensi).<br>
    3). Have you Impelement LSP? <br>
    Sudah, Sekarang carRepositoryImpl me implement carRepository, dan ini tidak membingungkan karena semua fungsi yang di inherit dari carRepository jelas. LSP itu sendiri adalah ketika kita membuat subclass/subtype dari suatu superclass, subclassnya tidak boleh memiliki behaviour yang berbeda dengan superclass.<br>
    4). Have you implement ISP? <br>
    Sudah, bisa dilihat pada repository terdapat interface-interface kecil yang memiliki satu fungsi saja, seperti Creatable, Deleteable, etc, dan interface tersebut di implement di interface yang lebih besar pada carRepository dan begitu juga jika ingin membuat class atau interface lain yang ingin mengeimplement salah satu dari interfacenya saja. ISP (Interface Segregation Principle) itu sendiri adalah prinsip yang menyatakan untuk menghindari interface yang besar sehingga kita tidak memaksa method-method yang tidak perlu pada suatu class yang akan mengimplement nya, sehingga lebih baik gunakan interface yang kecil seperti Creatable pada repo saya.<br>
    5).  Have you implement DIP? <br>
    Sudah, pada sebelumnya di carController dia membutuhkan dependency ke implement dari carRepository (Implement) sehingga dia bergantung langsung pada implementationnya. Dan sekarang saya membuat carRepostiroy menjadi interface, sehingga carController membutuhkan depencies ke interface (abstraction) bukan kepada implementationya. DIP (Dependency Inversion Principle) itu sendiri menyatakan bahwa module tingkat tinggi tidak boleh bergantung langsung dengan module tingkat rendah, keduanya harus bergantung pada abstraksi.<br>

2.  Explain the advantages of applying SOLID principles to your project with examples. <br>
    dengan menggunakan solid tentu aplikasi saya akan lebih mudah untuk di maintain, scale dan lebih fleksible. Untuk contoh pada aplikasi saya dengan menerapkan SRP pada carController maka akan lebih mudah untuk melakukan testing dan penerapan OCP pada carRepository semisal saya ingin extend fitur seperti membuat repostiroy khusus untuk mobil matic dan khusus untuk mobil manual saya bisa membuat yang baru tanpa perlu memodifikasi behaviour dan kode yang lama karena tetap mengimplementasi interface carRepository. <br>

3. Explain the disadvantages of not applying SOLID principles to your project with examples. <br>
   Jika kita tidak mengimplementasikan prinsip SOLID pada aplikasi, tentu akan menyusahkan kita semisal ingin me-maintain kode kita dalam jangka waktu yang panjang. Contoh semisal saya tidak menerapkan LSP dengan benar, saya memiliki interface carRepostiry dengan method untuk menghitung jumlah ban seluruh mobil yang tersimpan, tapi saya implementasikan ke repository untuk motor, dan mengubah behaviournya karena jumlah dari bannya beda, jika saya ingin melihat kode saya lagi dalam beberapa bulan kedepan, akan membuat saya bingung karena tidak konsisten. Untuk pelanggaran ISP, semisal saya punya interface besar yang kurang lebih bisa create update dan delete suatu objek, dan saya perlu membuat kelas implementasi baru tetapi hanya bisa lakukan create saja, dan jika saya immplement interface sebelumnya saya harus mengeimplementasi 3 method tersebut (create, update , delete) dan ini akan menyusahkan saya karena perlu membuat interface berbeda lagi untuk implementasi kelas tersebut. Dan terakhir jika tidak implementasi DIP, semisal saya ada sebuah service yang membutuhkan dependency dengan sebuah kelas implementasi repository, dan ternyata ada perubahan dbms sehingga pada repository harus ada yang diubah kodenya, tetapi karena kita gunakan implementasi langsung maka kita harus mengubah juga kode pada service, jika kita gunakan abstraksi pada service kita tidak perlu mengubah/memodifikasi lagi.
</details>

<details>
  <summary>
    Module 4
  </summary>

### Reflection <br>
1.  Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests. <br>
2.  You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests. <br>
    --- <br>

Jawaban  : <br>
1.  Menurut saya pendekatan coding dengan TDD sangat berguna, berapa kali ketika saya mengerjakan ini lupa melakukan hal-hal yang gampang terlewat, tetapi dikarenakan saya sudah membuat unit test sebelumnya jadi sadar. Contohnya ketika saya implementasi SetStatus pada paymentService, seharusnya jika kita set statusnya jadi success order yang bersangkutan juga sukses, tapi tadi saya lupa dan tertangkap oleh unit test yang telah saya buat. Memang dalam pembuatan unit test ini memakan waktu yang cukup lama karena harus memikirkan kemungkinan nya dan ditambah dengan saya sendiri yang kurang familiar dengan sintax mocks ini. sering kali saya terkena error yang tidak begitu jelas, seperti Object mocks yang tidak sengaja saya inisiasi ulang, sehingga ketika saya ingin mocks behaviournya dengna when() dia malah error, dan memakan waktu juga untuk menemukan masalahnya. Hal yang perlu saya tingkatkan lagi adalah tentang sintax dan cara kerja dari mockito ini, sehingga waktu pengerjaannya bisa lebih cepat.

2.  Untuk prinsip F.I.R.S.T
    1) Untuk Fast, Sepertinya unit test saya sudah cukup cepat untuk dijalankan berulang, saya sudah mengusahakan untuk menggunakan mocks daripada menggunakan object langsung sehingga unit test saya jauh lebih ringan.
    2) Isolated/Independent, Semua unit test saya berjalan sendiri dan tidak bergantung dengan unit test lain. Selain itu saya juga membuat unit test yang berbeda untuk tiap kasus.
    3) Repeateable, dikarenakan unit test saya cukup ringan dengan menggunakan mocks tadi, dan terisolasi sehingga hasil yang dihasilkan unit test cukup konsisten.
    4) Self-Validating, Untuk ini saya rasa sudah sangat baik, karena assertion yang dituliskan jelas dan sesuai dengan spesifikasi ditambah saya sendiri yang sudah berapa kali diselamatkan dengan unit test ini seperti yang saya ceritakan di soal nomor 1.
    5) Timely, Untuk ini sebagian besar test yang sudah berhasil menerapi Timely, tetapi ada juga yang harus saya ubah setelah implementasi, dikarenakan ada banyak sintax error atau kesalahan teknis yang baru terlihat setelah saya buat implementasinya. Seperti objects mocks tadi yang tidak sengaja saya inisiasi ulang di SetUp.

Untuk langkah yang harus saya lakukan kedepannya adalah harus lebih membiasakan diri dengan sintaks dan sisi teknis dari mockito dan unit testing ini.
</details>