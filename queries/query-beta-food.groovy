
Table Cuisine = opr.scanFilterV({it.type == 'CUISINE'})
/*
Table Ingredient = opr.scanFilterV({it.type == 'INGREDIENT'})//, 1000)

Table IngrPartOf = opr.scanFilterE({it.label == 'ingr_part_of'})//, 1000)

Table Recipe = opr.scanFilterV({it.id >=1000000 && it.id <= 1057690})//, 1000)

Table OfCuisine = opr.scanFilterE({it.label == 'of_cuisine'})//, 1000)

Table CuisineReference = new Table(["id", "name", "ref_id", "ref_name"],
        [
                [4101, 'East-African', 4100, 'African'],
                [4103, 'North-African', 4100, 'African'],
                [4104, 'South-African', 4100, 'African'],
                [4105, 'West-African', 4100, 'African'],
                [4107, 'chinese', 4106, 'China'],
                [4108, 'east_asian', 4163, 'asian'],
                [4110, 'japanese', 4109, 'Japan'],
                [4112, 'korean', 4111, 'Korea'],
                [4114, 'EasternEuropean_Russian', 4113, 'Eastern-Europe'],
                [4116, 'Central_SouthAmerican', 4119, 'South-America'],
                [4117, 'Mexican', 4118, 'mexico'],
                [4130, 'Southwestern', 4162, 'western'],
                [4132, 'Scandinavian', 4131, 'Scandinavia'],
                [4135, 'Indian', 4134, 'India'],
                [4141, 'Thailand', 4140, 'Thai'],
                [4143, 'Vietnamese', 4142, 'Vietnam'],
                [4145, 'Italian', 4146, 'Italy'],
                [4157, 'Germany', 4156, 'German'],
                [4149, 'Spain', 4150, 'Spanish_Portuguese'],
                [4154, 'France', 4155, 'French'],
                [4158, 'Irish', 4161, 'UK-and-Ireland']
        ])



CuisineReference = opr.rename(CuisineReference, [["id", "id_old"]])

CuisineReference = opr.tetaLeftJoin(opr.set(Cuisine, ["it.ref_id = it.id", "it.ref_name = it.name"]), CuisineReference, {it.id == it.id_old  })
CuisineReference = opr.rename(CuisineReference, [["ref_id", "id_n"], ["ref_name", "name"]])

//CuisineReference.print()

//OfCuisine.print()

Table OfCuisineRef = opr.step(Recipe, OfCuisine, Constants.OUTBOUND)
OfCuisineRef = opr.step(OfCuisineRef, CuisineReference, Constants.OUTBOUND)
OfCuisineRef = opr.project(OfCuisineRef, ["id", "id_n", "label", "name"])

//OfCuisineRef.print()


//Ingredients in n recipes

Table RareIngr = opr.beta(Ingredient, IngrPartOf, 1, {true}, Constants.BOTH, [], ["it.count = 0"], ["it.count = 1"], ["id"], [[aggr:"sum", func:"it.count", as:"count"]], [])
//Ingredient.print()

RareIngr = opr.select(RareIngr, {it.count && it.count < 10})
RareIngr.orderAsc(["count"])
//RareIngr.print()

IngrPartOf = opr.tetaJoin(IngrPartOf, opr.renameAll(RareIngr, "rare"), {it.id == it.rare_id})

IngrPartOf = opr.project(IngrPartOf, ["id", "id_n", "label"])
IngrPartOf.print()

Table E = opr.union(IngrPartOf, OfCuisineRef)

//Table R = opr.union(RareIngr, Recipe)

//Table CoCusine = opr.beta(Cuisine, E, 2, {true}, Constants.BOTH, [], [], [], ["id", "id_n"], [], [])

Table CoCusine = opr.step(Cuisine, E, Constants.INBOUND)
CoCusine = opr.step(CoCusine, E, Constants.INBOUND)
CoCusine = opr.step(CoCusine, E, Constants.OUTBOUND)
CoCusine = opr.step(CoCusine, E, Constants.OUTBOUND)

CoCusine = opr.project(CoCusine, ["id", "id_n"])
CoCusine = opr.select(CoCusine, {it.id > it.id_n})
CoCusine = opr.unique(CoCusine)

CoCusine.print()

Table result = opr.beta(Cuisine, CoCusine, 3, {true}, Constants.BOTH, ['knows'], ["it.rank = 100.0f"], ["it.rank = it.rank/it.c"], ['id_n'], [[aggr:"sum", func:"it.rank", as:"rank"]], ["it.rank = 0.0f"])

result.print()
*/

Table CuisineInf = new Table (["id", "rank", "E_id", "c", "id_n", "name", "idCount", "type", "E_id_n"],
[
        [4102, 119.19, 4102, 3, 4100, 'Moroccan', 4102, 'CUISINE', ""],
        [4100, 58.12, "", 6, 4102, 'African', 4100, 'CUISINE', 4100],
        [4113, 52.17, 4113, 4, 4109, 'Eastern-Europe', 4113, 'CUISINE', ""],
        [4118, 65.93, 4118, 8, 4111, 'mexico', 4118, 'CUISINE', ""],
        [4109, 86.31, "", 3, 4113, 'Japan', 4109, 'CUISINE', 4109],
        [4111, 143.47, "", 3, 4118, 'Korea', 4111, 'CUISINE', 4111],
        [4118, 104.89, "", 8, 4119, 'mexico', 4118, 'CUISINE', 4118],
        [4100, 208.68, "", 6, 4122, 'African', 4100, 'CUISINE', 4100],
        [4100, 58.12, "", 6, 4124, 'African', 4100, 'CUISINE', 4100],
        [4100, 532.76, "", 6, 4126, 'African', 4100, 'CUISINE', 4100],
        [4126, 76.90, "", 23, 4128, 'American', 4126, 'CUISINE', 4126],
        [4126, 76.90, "", 23, 4129, 'American', 4126, 'CUISINE', 4126],
        [4126, 30.28, "", 23, 4131, 'American', 4126, 'CUISINE', 4126],
        [4100, 136.12, "", 6, 4134, 'African', 4100, 'CUISINE', 4100],
        [4113, 35.07, "", 4, 4139, 'Eastern-Europe', 4113, 'CUISINE', 4113],
        [4163, 17.99, 4163, 9, 4140, 'asian', 4163, 'CUISINE', ""],
        [4126, 30.28, "", 23, 4142, 'American', 4126, 'CUISINE', 4126],
        [4126, 45.93, "", 23, 4144, 'American', 4126, 'CUISINE', 4126],
        [4118, 141.99, "", 8, 4146, 'mexico', 4118, 'CUISINE', 4118],
        [4126, 45.93, "", 23, 4147, 'American', 4126, 'CUISINE', 4126],
        [4100, 90.37, "", 6, 4150, 'African', 4100, 'CUISINE', 4100],
        [4126, 15.67, "", 23, 4153, 'American', 4126, 'CUISINE', 4126],
        [4118, 163.47, "", 8, 4155, 'mexico', 4118, 'CUISINE', 4118],
        [4126, 15.67, "", 23, 4156, 'American', 4126, 'CUISINE', 4126],
        [4118, 104.89, "", 8, 4161, 'mexico', 4118, 'CUISINE', 4118],
        [4111, 40.49, "", 3, 4162, 'Korea', 4111, 'CUISINE', 4111],
        [4111, 202.41, "", 3, 4163, 'Korea', 4111, 'CUISINE', 4111]
])

CuisineInf = opr.tetaJoin(CuisineInf, Cuisine, {it.id_n == it.id} )
CuisineInf = opr.project(CuisineInf, ["id_n", "name", "rank"])
CuisineInf.orderDesc("rank")
CuisineInf.print()
