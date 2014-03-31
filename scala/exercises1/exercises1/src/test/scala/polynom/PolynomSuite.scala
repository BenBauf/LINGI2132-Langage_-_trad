package polynom

import common.TestSuite

class PolynomSuite extends TestSuite {
  
  test("Addition of Polynoms") {
    val p1 = Polynom(1, 2, 3)
    val p2 = Polynom(1, 2, 3)
    val p3 = p1 + p2
    p3.maxDegree shouldBe 2
    p3.minDegree shouldBe 0
    p3(2) shouldBe 6
  }

  test("Equality on Polynoms") {
    val p1 = Polynom(1, 2, 3)
    val p2 = Polynom(1, 2, 3)
    val p3 = Polynom(1, 2, 1)
    p1 == p2 shouldBe true 
    p1 == p3 shouldBe false 
  }
  
  test("Substraction of Polynoms") {
    val p1 = Polynom(1, 2, 3)
    val p2 = Polynom(1, 2, 3)
    val p3 = p1 - p2
    p3.maxDegree shouldBe 0
    p3.minDegree shouldBe 0
    p3(0) shouldBe 0
    intercept[Exception] { p3(-1) }
  }
  
  // Add more tests
}