#!/bin/bash

if [ $# -ne 1 ]; then
    echo "É necessário $0 <NomeDaClasse>"
    exit 1
fi
_pwd=$(pwd)
base_path_clinica="$_pwd/src/main/java/com/project/pro"

nome_classe="$1"
nome_classe_dto="${nome_classe}DTO"
nome_repository="${nome_classe}Repository"
nome_service="${nome_classe}Service"
nome_service_impl="${nome_service}Impl"
nome_interface_controller="I${nome_classe}Controller"
nome_controller="${nome_classe}Controller"

nome_tabela=$(echo "pro_$nome_classe" | sed -r 's/([a-z])([A-Z])/\1_\2/g' | tr '[:upper:]' '[:lower:]')

echo $nome_tabela

entity_file_in="baseFiles/entity.txt"
repository_file_in="baseFiles/repository.txt"
service_file_in="baseFiles/service.txt"
service_impl_file_in="baseFiles/serviceImpl.txt"
controller_file_in="baseFiles/controller.txt"
icontroller_file_in="baseFiles/iController.txt"
dto_file_in="baseFiles/dto.txt"

entity_file_out="entidade.java"
repository_file_out="repositorio.java"
service_file_out="service.java"
service_impl_file_out="serviceImpl.java"
controller_file_out="controller.java"
icontroller_file_out="icontroller.java"
dto_file_out="dto.java"

primeira_letra_minuscula() {
    local input="$1"
    local primeira_letra="${input:0:1}"
    local restante="${input:1}"
    local resultado="$(tr '[:upper:]' '[:lower:]' <<<"$primeira_letra")$restante"
    echo "$resultado"
}

nome_variavel=$(primeira_letra_minuscula $nome_classe)
id_var_name="id$nome_classe"

sed -e "s/\"tableName\"/\"$nome_tabela\"/" \
    -e "s/className/$nome_classe/" 

sed -e "s/className/$nome_classe/g" \
    -e "s/repositoryName/$nome_repository/" "$repository_file_in" >"$repository_file_out"

sed -e "s/className/$nome_classe/g" \
    -e "s/dtoName/$nome_classe_dto/g" \
    -e "s/serviceName/$nome_service/g" \
    -e "s/idVarName/$id_var_name/g" \
    -e "s/varName/$nome_variavel/g" "$service_file_in" >"$service_file_out"

sed -e "s/className/$nome_classe/g" \
    -e "s/dtoName/$nome_classe_dto/g" \
    -e "s/serviceName/$nome_service/g" \
    -e "s/idVarName/$id_var_name/g" \
    -e "s/serviceImplName/$nome_service_impl/g" \
    -e "s/varName/$nome_variavel/g" "$service_impl_file_in" >"$service_impl_file_out"

sed -e "s/className/$nome_classe/g" \
    -e "s/dtoName/$nome_classe_dto/g" \
    -e "s/serviceName/$nome_service/g" \
    -e "s/idVarName/$id_var_name/g" \
    -e "s/varName/$nome_variavel/g" \
    -e "s/IControllerName/$nome_interface_controller/g" \
    -e "s/controllerName/$nome_controller/g" "$controller_file_in" >"$controller_file_out"

sed -e "s/className/$nome_classe/g" \
    -e "s/dtoName/$nome_classe_dto/g" \
    -e "s/serviceName/$nome_service/g" \
    -e "s/idVarName/$id_var_name/g" \
    -e "s/varName/$nome_variavel/g" \
    -e "s/iControllerName/$nome_interface_controller/g" "$icontroller_file_in" >"$icontroller_file_out"

sed -e "s/dtoName/$nome_classe_dto/g" "$dto_file_in" >"$dto_file_out"

mv "$entity_file_out" "$base_path_common/entity/$nome_classe.java"
mv "$repository_file_out" "$base_path_clinica/repository/${nome_repository}.java"
mv "$service_file_out" "$base_path_clinica/service/${nome_service}.java"
mv "$service_impl_file_out" "$base_path_clinica/service/impl/${nome_service_impl}.java"
mv "$controller_file_out" "$base_path_clinica/controller/${nome_controller}.java"
mv "$icontroller_file_out" "$base_path_clinica/controller/interfaces/${nome_interface_controller}.java"
mv "$dto_file_out" "$base_path_common/dto/${nome_classe_dto}.java"

read -p "Deseja adicionar os arquivos criados ao git? [S/N] " adicionar

if [[ -n "$adicionar" && "$adicionar" == [sS] ]]; then
    git add .
fi
