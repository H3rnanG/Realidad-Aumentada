import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './home.component.html',
  styleUrls: ['././home.component.css']
})
export class HomeComponent {
  title = 'proyecto';
  selectedFileName: string | undefined;
  videoStream: MediaStream | undefined;

  @ViewChild('fileInput') fileInput: ElementRef<HTMLInputElement> | undefined;
  @ViewChild('video') video: ElementRef<HTMLVideoElement> | undefined;

  constructor() { }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    this.selectedFileName = file ? file.name : undefined;
  }

  openCamera(): void {
    navigator.mediaDevices.getUserMedia({ video: true })
      .then((stream) => {
        this.videoStream = stream;
        if (this.video) {
          this.video.nativeElement.srcObject = stream;
          this.video.nativeElement.play();
        }
        if (this.fileInput) {
          this.fileInput.nativeElement.disabled = true;
        }
      })
      .catch((error) => {
        console.error('Error al acceder a la cámara:', error);
      });
  }

  closeCamera(): void {
    if (this.videoStream) {
      this.videoStream.getTracks().forEach((track) => {
        track.stop();
      });
    }
    if (this.video) {
      this.video.nativeElement.srcObject = null;
    }
    if (this.fileInput) {
      this.fileInput.nativeElement.disabled = false;
    }
  }

  onSubmit(): void {

  }
}
