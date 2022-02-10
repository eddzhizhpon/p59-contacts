import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Contact } from '@domain/contact';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class ContactRestService {

  private wsUrl = environment.wsUrl;

  constructor(private http: HttpClient) { }

  create(contact: Contact): Observable<Contact> {
    return this.http.put<Contact>(this.wsUrl, contact);
  }

  read(id: number): Observable<Contact> {
    return this.http.get<Contact>(this.wsUrl + '/' + id);
  }

  update(contact: Contact): Observable<Contact> {
    return this.http.post<Contact>(this.wsUrl, contact);
  }

  delete(contact: Contact): Observable<ArrayBuffer> {
    return this.http.delete<ArrayBuffer>(this.wsUrl + '/' + contact.id);
  }

  list(): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.wsUrl);
  }
}
