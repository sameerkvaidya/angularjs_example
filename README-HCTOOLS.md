angularjs_example
=================
Couchbase view 

function (doc, meta) {
  if(doc.type == 'env')
    emit(doc.name+'*'+doc.url, doc.user +' ['+ doc.desc+']');
}


Document Example
{
   "id": "ENV::17",
   "user": "Operations",
   "name": "HC UAT",
   "desc": "UAT",
   "type": "env",
   "url": "http://google.com",
   "created": "Oct 6, 2013 3:25:07 PM",
   "updated": "Oct 6, 2013 9:45:13 PM"
}
