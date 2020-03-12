package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
)

const url = "http://q.kdpt.net/api"

func main() {
	request, err := http.NewRequest(http.MethodGet, url, nil)
	if err != nil {
		panic(err)
	}
	query := request.URL.Query()
	query.Add("id", "*********************")
	query.Add("com", "yunda")
	query.Add("nu", "************")
	query.Add("show", "json")

	request.URL.RawQuery = query.Encode()
	response, err := http.DefaultClient.Do(request)
	if err != nil {
		panic(err)
	}
	body, err := ioutil.ReadAll(response.Body)
	if err != nil {
		panic(err)
	}
	//decoder := json.NewDecoder(response.Body)

	fmt.Println(string(body))
}
