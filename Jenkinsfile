pipeline {
    agent any

    environment {
        GITHUB_URL = 'https://github.com/MeryemEceAYYILDIZ/E-Turnuva.git'
        IMAGE_NAME = 'eturnuva-app'
    }

    stages {
        // AŞAMA 1: Github'dan Kod Çekme
        stage('1. Checkout from Git') {
            steps {
                echo 'Githubdan kodlar çekiliyor...'
                git branch: 'main', url: "${GITHUB_URL}"
            }
        }

        // AŞAMA 2: Kodları Derleme
        stage('2. Build Project') {
            steps {
                echo 'Proje derleniyor...'
                // Testleri şimdilik atlıyoruz, sadece build alıyoruz
                bat 'mvn clean package -DskipTests'
            }
        }

        // AŞAMA 3: Birim Testleri
        stage('3. Unit Tests') {
            steps {
                echo 'Birim testleri (Service Testleri) çalıştırılıyor...'
                // Sadece sonu ServiceTest ile biten testleri çalıştır
                bat 'mvn test -Dtest=*ServiceTest'
            }
            post {
                always {
                    // Test sonuçlarını Jenkins arayüzüne raporla
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        // AŞAMA 4: Entegrasyon Testleri
        stage('4. Integration Tests') {
            steps {
                echo 'Entegrasyon testleri (Controller Testleri) çalıştırılıyor...'
                // Sadece IntegrationTest içerenleri çalıştır
                bat 'mvn test -Dtest=*IntegrationTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        // AŞAMA 5: Docker Container Çalıştırma
            stage('5. Deploy to Docker') {
                steps {
                    echo 'Sistem Docker üzerinde ayağa kaldırılıyor...'
                    script {
                        // 1. Önceki kalıntıları ZORLA sil
                        // Hata vermemesi için '|| exit 0' (Varsa siler, yoksa devam eder)
                        bat 'docker rm -f eturnuva-postgres || exit 0'
                        bat 'docker rm -f eturnuva-app || exit 0'

                        // 2. Normal temizlik (Ağları vs. temizler)
                        bat 'docker-compose down || exit 0'

                        // 3. İmajı oluştur (eclipse-temurin hatası düzeltilmiş haliyle)
                        bat 'docker build -t eturnuva-app .'

                        // 4. Sistemi başlat
                        bat 'docker-compose up -d'

                        echo 'Uygulamanın ayağa kalkması bekleniyor (30 saniye)...'
                        sleep 30
                    }
                }
            }

        // AŞAMA 6: Sistem Testleri - Selenium
        stage('6. System Tests (Selenium)') {
            steps {
                echo 'Selenium testleri çalıştırılıyor...'
                // Docker'da çalışan sisteme dışarıdan test isteği atıyoruz
                bat 'mvn test -Dtest=SeleniumSystemTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    // Pipeline bitince temizlik yap
    post {
        always {
            echo 'Pipeline tamamlandı. Temizlik yapılıyor...'
            // Test bittikten sonra Docker'ı kapat
            bat 'docker-compose down'
        }
    }
}